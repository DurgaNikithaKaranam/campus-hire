import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";

function Login({ setUser }) {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

 const navigate = useNavigate();

const handleLogin = async (e) => {
  e.preventDefault();

  try {
    const res = await fetch("http://localhost:8080/student/api/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ email, password }),
    });

    const data = await res.json();

    if (data.status === "error") {
      alert(data.message);
      return;
    }

    localStorage.setItem("user", JSON.stringify(data.user));
    setUser(data.user);

    // ✅ FIXED REDIRECT
    if (data.user.role === "admin") {
      navigate("/admin-dashboard");
    } else {
      navigate("/dashboard");
    }

  } catch (error) {
    console.error("LOGIN ERROR:", error);
  }
};
  return (
    <div style={styles.container}>

      {/* LEFT SIDE */}
      <div style={styles.left}>
        <h1>Login</h1>
        <p>
          Get access to internships, jobs and track your applications easily.
        </p>
      </div>

      {/* RIGHT SIDE */}
      <div style={styles.right}>
        <form onSubmit={handleLogin} style={styles.form}>

          <input
            type="email"
            placeholder="Enter Email"
            required
            style={styles.input}
            onChange={(e) => setEmail(e.target.value)}
          />

          <input
            type="password"
            placeholder="Enter Password"
            required
            style={styles.input}
            onChange={(e) => setPassword(e.target.value)}
          />

          <button type="submit" style={styles.button}>Login</button>

          <p style={{ marginTop: "15px" }}>
            New user? <Link to="/register">Create an account</Link>
          </p>

        </form>
      </div>

    </div>
  );
}

const styles = {
  container: {
    display: "flex",
    height: "100vh",
    fontFamily: "Arial"
  },
  left: {
    width: "40%",
    background: "#2874f0",
    color: "white",
    padding: "40px"
  },
  right: {
    width: "60%",
    display: "flex",
    justifyContent: "center",
    alignItems: "center"
  },
  form: {
    width: "300px"
  },
  input: {
    width: "100%",
    padding: "10px",
    marginBottom: "20px",
    border: "none",
    borderBottom: "1px solid #ccc"
  },
  button: {
    width: "100%",
    padding: "10px",
    background: "#fb641b",
    color: "white",
    border: "none",
    borderRadius: "5px",
    cursor: "pointer"
  }
};

export default Login;