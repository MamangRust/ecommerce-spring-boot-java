import { fetchCategoryById, updateCategoryById } from "@/redux/category";
import { SweetAlert } from "@/helpers";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";

const EditCategoryPage = () => {
  const { id } = useParams();
  const dispatch = useDispatch();

  const categoryState = useSelector((state) => state.categoryReducer);

  const { category, loading, error } = categoryState;

  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [file, setFile] = useState(null);
  const [imageName, setImageName] = useState("");

  useEffect(() => {
    dispatch(fetchCategoryById(id));
  }, [dispatch]);

  useEffect(() => {
    if (category) {
      setName(category.data.name);
      setDescription(category.data.description);
      setImageName(category.data.image_path)
      
    }
  }, [category]);

  const handleSubmit = (e) => {
    e.preventDefault();
    const formData = new FormData();

    const idInt = parseInt(id);
    
  
    
    formData.append("id", idInt);
    formData.append("name", name);

    if (file) {
      formData.append("file", file);
    }


    dispatch(updateCategoryById(formData)).then((data) => {
      SweetAlert.success("Success", "Category berhasil diupdate").then(() => {
        navigate("/admin/category");
      });
    });
  };

  return (
    <div className="page-heading">
      <div className="page-title">
        <div className="row">
          <div className="col-12 col-md-6 order-md-1 order-last">
            <h3>Edit Category</h3>
          </div>
          <div className="col-12 col-md-6 order-md-2 order-first">
            <nav
              aria-label="breadcrumb"
              className="breadcrumb-header float-start float-lg-end"
            >
              <ol className="breadcrumb">
                <li className="breadcrumb-item">
                  <a href="index.html">Dashboard</a>
                </li>
                <li className="breadcrumb-item active" aria-current="page">
                  Edit Category
                </li>
              </ol>
            </nav>
          </div>
        </div>
      </div>
      <section className="section">
        <div className="card">
          <div className="card-header">
            <h3>Edit Category</h3>
          </div>
          <div className="card-body">
            {error && (
              <div
                className="alert alert-danger alert-dismissible fade show"
                role="alert"
              >
                {error.message}
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="alert"
                  aria-label="Close"
                ></button>
              </div>
            )}
            {loading ? (
              <h1>Loading</h1>
            ) : (
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label htmlFor="nama_kategori" className="form-label">
                    Nama Kategori
                  </label>
                  <input
                    type="text"
                    name="nama_kategori"
                    value={name}
                    className="form-control"
                    id="nama_kategori"
                    onChange={(e) => setName(e.target.value)}
                    required
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="description" className="form-label">
                    Deskripsi Kategori
                  </label>
                  <textarea
                    name="description"
                    value={description}
                    className="form-control"
                    id="description"
                    onChange={(e) => setDescription(e.target.value)}
                    required
                  ></textarea>
                </div>
                <div className="mb-3">
                  <label htmlFor="image" className="form-label">
                    Gambar
                  </label>
                  <input
                    type="file"
                    name="image"
                    className="form-control"
                    id="image"
                    onChange={(e) => {
                      const selectedFile = e.target.files[0];
                      setFile(selectedFile || null);
                    }}
                  />
                </div>
                {imageName && (
                  <div className="mb-3">
                    <label htmlFor="current_image" className="form-label">
                      Gambar Saat Ini
                    </label>
                    <br />
                    {imageName ? (
                      <img
                        src={`${imageName}`}
                        alt="Current Image"
                        width={100}
                        height={100}
                      />
                    ) : (
                      <span>Tidak ada gambar</span>
                    )}
                  </div>
                )}
                <button type="submit" className="btn btn-primary">
                  Submit
                </button>
              </form>
            )}
          </div>
        </div>
      </section>
    </div>
  );
};

export default EditCategoryPage;
