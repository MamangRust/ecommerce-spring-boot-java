import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { myApi } from '@/helpers/api';

export const createUser = createAsyncThunk(
  'users/create',
  async (createUserData, { rejectWithValue, getState }) => {
    try {
      const { accessToken } = getState().loginReducer;

      const response = await myApi.post('/users/create', createUserData, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      return response.data;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

export const fetchUsers = createAsyncThunk(
  'users/fetchAll',
  async (_, { rejectWithValue, getState }) => {
    try {
      const { accessToken } = getState().loginReducer;

      const response = await myApi.get('/users', {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      return response.data.data;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

export const fetchUserById = createAsyncThunk(
  'users/fetchById',
  async (id, { rejectWithValue, getState }) => {
    try {
      const { accessToken } = getState().loginReducer;

      const response = await myApi.get(`/users/${id}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      return response.data.data
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

export const updateUserById = createAsyncThunk(
  'users/updateById',
  async (formData, { rejectWithValue, getState }) => {
    try {
      const { accessToken } = getState().loginReducer;

      const response = await myApi.put(`/users/update`, formData, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          'Content-Type': 'multipart/form-data',
        },
      });

      return response.data.data
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

export const deleteUserById = createAsyncThunk(
  'users/deleteById',
  async (id, { rejectWithValue, getState }) => {
    try {
      const { accessToken } = getState().loginReducer;

      await myApi.delete(`/users/delete/${id}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      return id;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

const initialState = {
  user: null,
  users: [],
  loading: false,
  error: null,
};

const usersSlice = createSlice({
  name: 'users',
  initialState: initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(createUser.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(createUser.fulfilled, (state, action) => {
        state.loading = false;
        state.users.push(action.payload);
      })
      .addCase(createUser.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      .addCase(fetchUsers.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchUsers.fulfilled, (state, action) => {
        state.loading = false;
        state.users = action.payload;
      })
      .addCase(fetchUsers.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      .addCase(fetchUserById.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchUserById.fulfilled, (state, action) => {
        state.loading = false;
        state.user = action.payload;
      })
      .addCase(fetchUserById.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      .addCase(updateUserById.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(updateUserById.fulfilled, (state, action) => {
        state.loading = false;
        const userIndex = state.users.findIndex(
          (user) => user.user_id === action.payload.id
        );
        if (userIndex !== -1) {
          state.users[userIndex] = action.payload;
        }
      })
      .addCase(updateUserById.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      .addCase(deleteUserById.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(deleteUserById.fulfilled, (state, action) => {
        state.loading = false;
        state.users = state.users.filter(
          (user) => user.user_id !== action.payload
        );
      })
      .addCase(deleteUserById.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      });
  },
});

export default usersSlice.reducer;
