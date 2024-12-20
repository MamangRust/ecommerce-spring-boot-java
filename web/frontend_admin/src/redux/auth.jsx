import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { myApi, decodeToken } from '@/helpers';

let refreshToken;
let accessToken;

if (typeof window !== 'undefined') {
  refreshToken = window.localStorage.getItem('refreshToken') || null;
  accessToken = window.localStorage.getItem('accessToken') || null;
}

const initialUser = accessToken ? decodeToken(accessToken) : null;

export const registerAction = createAsyncThunk(
  'auth/register',
  async (registerData, { rejectWithValue }) => {
    try {
      const response = await myApi.post('/auth/register', registerData);
      return response.data;
    } catch (error) {
      if (error.response && error.response.data) {
        return rejectWithValue(error.response.data);
      } else {
        return rejectWithValue('An error occurred while registering.');
      }
    }
  }
);

export const loginAction = createAsyncThunk(
  'auth/login',
  async (loginData, { rejectWithValue }) => {
    try {
      const response = await myApi.post('/auth/login', loginData);

      

     

      localStorage.setItem('accessToken', response.data.data.access_token);

      localStorage.setItem('refreshToken', response.data.data.refresh_token);

      return response.data.data;
    } catch (error) {
      if (error.response && error.response.data) {
        return rejectWithValue(error.response.data);
      } else {
        return rejectWithValue('An error occurred while logging in.');
      }
    }
  }
);

export const logoutUserAction = () => (dispatch) => {
  localStorage.removeItem('accessToken');
  localStorage.removeItem('refreshToken');
  dispatch(clearAuthData());
};

export const updateTokenAsync = createAsyncThunk(
  'auth/updateToken',
  async (_, { getState, dispatch }) => {
    try {
      const { refreshToken } = getState().loginReducer;

      if (!refreshToken) {
        return;
      }

      const response = await myApi.post('/auth/refresh-token?refreshToken=' + encodeURIComponent(refreshToken));

      localStorage.setItem('accessToken', response.data.data.access_token);

      localStorage.setItem('refreshToken', response.data.data.refresh_token);

      localStorage.setItem('accessToken', data.access);
    } catch (error) {
      throw error;
    }
  }
);

export const registerSlice = createSlice({
  name: 'register',
  initialState: { loading: false, success: false, error: null },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(registerAction.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(registerAction.fulfilled, (state, action) => {
        state.loading = false;
        state.success = true;
      })
      .addCase(registerAction.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      });
  },
});

export const loginSlice = createSlice({
  name: 'login',
  initialState: {
    user: initialUser,
    accessToken: accessToken,
    refreshToken: refreshToken,
    loading: null,
  },
  reducers: {
    setUser: (state, action) => {
      state.user = action.payload;
    },
    setAuthTokens: (state, action) => {
      state.authTokens = action.payload;
    },
    setLoading: (state, action) => {
      state.loading = action.payload;
    },
    clearAuthData: (state) => {
      state.user = null;
      state.authTokens = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(loginAction.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(loginAction.fulfilled, (state, action) => {
        state.loading = false;

        state.accessToken = action.payload.access_token;
        state.refreshToken = action.payload.refresh_token;

        state.user = decodeToken(action.payload.access_token);
      })
      .addCase(loginAction.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message;
      });
  },
});

export const { setUser, setAuthTokens, setLoading, clearAuthData } =
  loginSlice.actions;

export default {
  loginSlice: loginSlice.reducer,
  registerSlice: registerSlice.reducer,
};
