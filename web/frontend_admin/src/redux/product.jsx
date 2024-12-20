import { myApi } from "@/helpers";
import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";

export const fetchAllProducts = createAsyncThunk(
  "products/fetchAll",
  async (_, { rejectWithValue }) => {
    try {
      const response = await myApi.get("/products");
      return response.data.data;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

export const fetchProductById = createAsyncThunk(
  "products/fetchById",
  async (id, { rejectWithValue, getState }) => {
    try {
      const { accessToken } = getState().loginReducer;
      
      const response = await myApi.get(`/products/id/${id}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });

      console.log("token", accessToken);
      
      console.log("product axios response: ", response);
      
      // Add null checks and proper response handling
      if (!response || !response.data) {
        throw new Error('Invalid response format');
      }
      
   
      return response.data.data
      
    } catch (error) {
      console.error("Error in fetchProductById:", error);
      return rejectWithValue(
        error.response?.data || { 
          message: error.message || 'Failed to fetch product' 
        }
      );
    }
  }
);

export const createProduct = createAsyncThunk(
  "products/create",
  async (formData, { getState, rejectWithValue }) => {
    try {
      const { accessToken } = getState().loginReducer;
      const response = await myApi.post("/products/create", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${accessToken}`,
        },
      });
      console.log("Hello bisa", response.data);
      return response.data;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

export const updateProductById = createAsyncThunk(
  "products/updateById",
  async (form, { getState, rejectWithValue }) => {
    try {
      const { accessToken } = getState().loginReducer;

      
      const response = await myApi.post(`/products/update`, form, {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${accessToken}`,
        },
      });
      return response.data;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

export const deleteProduct = createAsyncThunk(
  "products/deleteById",
  async (id, { rejectWithValue, getState }) => {
    try {
      const { accessToken } = getState().loginReducer;
      const response = await myApi.delete(`/products/delete/${id}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      return response.data;
    } catch (error) {
      if (error.response && error.response.status === 404) {
        // Produk tidak ada
        return rejectWithValue("Produk tidak ada");
      } else if (error.response && error.response.status === 403) {
        // Anda tidak memiliki izin untuk menghapus produk tersebut
        return rejectWithValue(
          "Anda tidak memiliki izin untuk menghapus produk tersebut"
        );
      } else {
        // Ada kesalahan lain
        return rejectWithValue("Gagal menghapus produk");
      }
    }
  }
);

const initialState = {
  product: null,
  products: [],
  selectedProduct: null,
  loading: false,
  error: null,
};

const productsSlice = createSlice({
  name: "products",
  initialState: initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchAllProducts.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchAllProducts.fulfilled, (state, action) => {
        state.loading = false;
        state.products = action.payload;
      })
      .addCase(fetchAllProducts.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      .addCase(fetchProductById.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchProductById.fulfilled, (state, action) => {
        state.loading = false;

        console.log("payload: ", action.payload)
        
        state.product = action.payload
      })
      .addCase(fetchProductById.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      .addCase(createProduct.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(createProduct.fulfilled, (state, action) => {
        state.loading = false;
        state.products.push(action.payload);
      })
      .addCase(createProduct.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      .addCase(updateProductById.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(updateProductById.fulfilled, (state, action) => {
        state.loading = false;
        const productIndex = state.products.findIndex(
          (product) => product.id === action.payload.id
        );
        if (productIndex !== -1) {
          state.products[productIndex] = action.payload;
        }
      })
      .addCase(updateProductById.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      .addCase(deleteProduct.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(deleteProduct.fulfilled, (state, action) => {
        state.loading = false;
        state.products = state.products.filter(
          (product) => product.id !== action.payload
        );
      })
      .addCase(deleteProduct.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      });
  },
});

export default productsSlice.reducer;
