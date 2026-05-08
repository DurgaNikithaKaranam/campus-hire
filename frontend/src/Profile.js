import Navbar from "./Navbar";

function Profile() {

  const user = JSON.parse(localStorage.getItem("user"));

  return (
    <div style={{ background: "#f5f6fa", minHeight: "100vh" }}>
      
      <Navbar />

      <div style={styles.card}>

        <h2>👤 Profile</h2>

        <p><b>Name:</b> {user?.name || "Admin"}</p>
        <p><b>Email:</b> {user?.email}</p>

        {/* Student only */}
        {user?.role === "student" && (
          <>
            <p><b>Branch:</b> {user?.branch}</p>
            <p><b>Year:</b> {user?.year}</p>
          </>
        )}

      </div>

    </div>
  );
}

const styles = {
  card: {
    background: "white",
    padding: "20px",
    margin: "40px auto",
    width: "60%",
    borderRadius: "10px",
    boxShadow: "0 2px 8px rgba(0,0,0,0.1)"
  }
};

export default Profile;