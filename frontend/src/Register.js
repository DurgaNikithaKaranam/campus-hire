import { useState } from "react";
import { Link } from "react-router-dom";

function Register() {

  const [form, setForm] = useState({
    name: "",
    email: "",
    password: "",
    branch: "",
    year: ""
  });
  console.log("FORM:", form);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleRegister = async (e) => {
    e.preventDefault();

    const res = await fetch("http://localhost:8080/student/api/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(form)
    });

    const text = await res.text();

    alert(text);

    if (text.includes("Successfully")) {
      window.location.href = "/";
    }
  };

  return (
    <div style={styles.container}>

      {/* LEFT */}
      <div style={styles.left}>
        <h1>Register</h1>
        <p>Create your account and explore opportunities.</p>
      </div>

      {/* RIGHT */}
      <div style={styles.right}>
        <form onSubmit={handleRegister} style={styles.form}>
           
          <input name="name" placeholder="Name" required style={styles.input} onChange={handleChange} />

          <input name="email" type="email" placeholder="Email" required style={styles.input} onChange={handleChange} />

          <input name="password" type="password" placeholder="Password" required style={styles.input} onChange={handleChange} />

          <input name="branch" placeholder="Branch (CSE/IT/etc)" required style={styles.input} onChange={handleChange} />

          <input name="year" type="number" placeholder="Year" required style={styles.input} onChange={handleChange} />

          <button type="submit" style={styles.button}>Register</button>

          <p style={{ marginTop: "15px" }}>
            Already have account? <Link to="/">Login</Link>
          </p>

        </form>
      </div>

    </div>
  );
}

const styles = {
  container: { display: "flex", height: "100vh" },
  left: { width: "40%", background: "#2874f0", color: "white", padding: "40px" },
  right: { width: "60%", display: "flex", justifyContent: "center", alignItems: "center" },
  form: { width: "300px" },
  input: {
    width: "100%",
    padding: "10px",
    marginBottom: "15px",
    border: "none",
    borderBottom: "1px solid #ccc"
  },
  button: {
    width: "100%",
    padding: "10px",
    background: "#fb641b",
    color: "white",
    border: "none",
    borderRadius: "5px"
  }
};

export default Register;