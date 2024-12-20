import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { myApi } from '../helpers/api';

export const fetchProvinces = createAsyncThunk(
  'rajaOngkir/fetchProvinces',
  async (_, { getState, rejectWithValue }) => {
    try {
      const accessToken = getState().loginReducer.accessToken;

      if (!accessToken) {
        console.error('accessToken is null');
        return rejectWithValue('accessToken is null');
      }

      const response = await myApi.get('/rajaongkir/provinsi', {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });

     

      return response.data.data.rajaongkir.results;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

export const fetchCities = createAsyncThunk(
  'rajaOngkir/fetchCities',
  async (provId, { getState, rejectWithValue }) => {
    try {
      const accessToken = getState().loginReducer.accessToken;

      if (!accessToken) {
        console.error('accessToken is null');
        return rejectWithValue('accessToken is null');
      }

      const response = await myApi.get(`/rajaongkir/city/${provId}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });

      console.log('Result Cities: ', response.data.data);

      return response.data.data.rajaongkir.results;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

export const calculateShippingCost = createAsyncThunk(
  'rajaOngkir/calculateShippingCost',
  async ({ asal, tujuan, berat, kurir }, { rejectWithValue, getState }) => {
    try {
      const accessToken = getState().loginReducer.accessToken;

      if (!accessToken) {
        console.error('accessToken is null');
        return rejectWithValue('accessToken is null');
      }

      const response = await myApi.post(
        `/rajaongkir/cost`,
        {
          asal,
          tujuan,
          berat,
          kurir,
        },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );

      console.log('Result Calculate: ', response.data.data);

      return response.data.data.rajaongkir.results;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

const initialState = {
  provinces: [],
  cities: [],
  shippingCosts: [],
  loading: false,
  error: null,
};

const rajaOngkirSlice = createSlice({
  name: 'rajaOngkir',
  initialState: initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchProvinces.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchProvinces.fulfilled, (state, action) => {
        state.loading = false;

        console.log('Payload: ', action.payload);

        state.provinces = action.payload;
      })
      .addCase(fetchProvinces.rejected, (state, action) => {
        state.loading = false;

        console.log('Error: ', action.error.message);
        state.error = action.error.message;
      })
      .addCase(fetchCities.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchCities.fulfilled, (state, action) => {
        state.loading = false;
        state.cities = action.payload;
      })
      .addCase(fetchCities.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message;
      })
      .addCase(calculateShippingCost.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(calculateShippingCost.fulfilled, (state, action) => {
        state.loading = false;
        state.shippingCosts = action.payload;
      })
      .addCase(calculateShippingCost.rejected, (state, action) => {
        state.loading = false;

        console.log('Error Calculate:', action.error.message);

        state.error = action.error.message;
      });
  },
});

export default rajaOngkirSlice.reducer;
