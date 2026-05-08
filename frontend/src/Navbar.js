
// import { useNavigate } from "react-router-dom";

// function Navbar() {

//   const navigate = useNavigate();
//   const user = JSON.parse(localStorage.getItem("user"));

//   return (
//     <div style={styles.nav}>

//       {/* LEFT */}
//       <h3>🎓 Career Portal</h3>

//       {/* CENTER */}
//       <div>

//         {/* 👨‍💼 ADMIN NAV */}
//         {user?.role === "admin" && (
//           <>
//             <span onClick={() => navigate("/admin-dashboard")} style={styles.link}>
//               Dashboard
//             </span>

//             <span onClick={() => navigate("/opportunities")} style={styles.link}>
//               Manage Jobs
//             </span>

//             <span onClick={() => navigate("/admin-applications")} style={styles.link}>
//               Applications
//             </span>
//           </>
//         )}

//         {/* 👨‍🎓 STUDENT NAV */}
//         {user?.role === "student" && (
//           <>
//             <span onClick={() => navigate("/dashboard")} style={styles.link}>
//               Dashboard
//             </span>

//             <span onClick={() => navigate("/opportunities")} style={styles.link}>
//               Opportunities
//             </span>

//             <span onClick={() => navigate("/my-applications")} style={styles.link}>
//               My Applications
//             </span>
//           </>
//         )}
//         <span
//   onClick={() => {
//     localStorage.removeItem("user");
//     window.location.href = "/";
//   }}
//   style={styles.link}
// >
//   Logout
// </span>

//       </div>

//       {/* RIGHT */}
//       <div>
//         👤 {user?.role === "admin" ? "Admin" : user?.name}
//       </div>

//     </div>
//   );
// }

// const styles = {
//   nav: {
//     display: "flex",
//     justifyContent: "space-between",
//     padding: "15px 30px",
//     background: "white",
//     boxShadow: "0 2px 5px rgba(0,0,0,0.1)"
//   },
//   link: {
//     margin: "0 10px",
//     cursor: "pointer"
//   }
// };

// export default Navbar;

import { useNavigate } from "react-router-dom";
import { useState } from "react";

function Navbar() {

  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem("user"));

  const [open, setOpen] = useState(false);

  return (
    <div style={styles.nav}>

      {/* LEFT */}
      <h3>🎓 Career Portal</h3>

      {/* CENTER */}
      <div>

        {/* ADMIN */}
        {user?.role === "admin" && (
          <>
            <span onClick={() => navigate("/admin-dashboard")} style={styles.link}>
              Dashboard
            </span>

            <span onClick={() => navigate("/opportunities")} style={styles.link}>
              Manage Jobs
            </span>

            <span onClick={() => navigate("/admin-applications")} style={styles.link}>
              Applications
            </span>
          </>
        )}

        {/* STUDENT */}
        {user?.role === "student" && (
          <>
            <span onClick={() => navigate("/dashboard")} style={styles.link}>
              Dashboard
            </span>

            <span onClick={() => navigate("/opportunities")} style={styles.link}>
              Opportunities
            </span>

            <span onClick={() => navigate("/my-applications")} style={styles.link}>
              My Applications
            </span>
          </>
        )}

      </div>

      {/* RIGHT - DROPDOWN */}
      <div style={{ position: "relative" }}>

        <div
          onClick={() => setOpen(!open)}
          style={styles.userBox}
        >
          👤 {user?.role === "admin" ? "Admin" : user?.name} ▼
        </div>

        {open && (
          <div style={styles.dropdown}>

            <div
              style={styles.dropdownItem}
              onClick={() => {
                navigate("/profile");
                setOpen(false);
              }}
            >
              Profile
            </div>

            <div
              style={styles.dropdownItem}
              onClick={() => {
                localStorage.removeItem("user");
                window.location.href = "/";
              }}
            >
              Logout
            </div>

          </div>
        )}

      </div>

    </div>
  );
}

const styles = {
  nav: {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    padding: "15px 30px",
    background: "white",
    boxShadow: "0 2px 5px rgba(0,0,0,0.1)"
  },

  link: {
    margin: "0 10px",
    cursor: "pointer"
  },

  userBox: {
    cursor: "pointer",
    padding: "8px 12px",
    borderRadius: "8px",
    background: "#f1f3f6"
  },

  dropdown: {
    position: "absolute",
    right: 0,
    top: "40px",
    background: "white",
    borderRadius: "8px",
    boxShadow: "0 4px 10px rgba(0,0,0,0.1)",
    width: "150px"
  },

  dropdownItem: {
    padding: "10px",
    cursor: "pointer",
    borderBottom: "1px solid #eee"
  }
};

export default Navbar;