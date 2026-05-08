import { BrowserRouter, Routes, Route } from "react-router-dom";
import { useState } from "react";

import Login from "./Login";
import Register from "./Register";
import StudentDashboard from "./StudentDashboard";
import AdminDashboard from "./AdminDashboard";
import Opportunities from "./Opportunities";
import MyApplications from "./MyApplications";
import AddJob from "./AddJob";
import AdminApplications from "./AdminApplications";
import Profile from "./Profile";


function App() {

  const [user, setUser] = useState(
    JSON.parse(localStorage.getItem("user"))
  );

  return (
    <BrowserRouter>
      <Routes>

        <Route path="/"
           element={ !user ? (
            <Login setUser={setUser} />
          ) : user.role === "admin" ? (
           <AdminDashboard />
          ) : (
          <StudentDashboard />
        )
       }
      />

        {/* STUDENT */}
        <Route
          path="/dashboard"
          element={user ? <StudentDashboard /> : <Login setUser={setUser} />}
        />

        <Route
          path="/my-applications"
          element={user ? <MyApplications /> : <Login setUser={setUser} />}
        />

        {/* ADMIN */}
        <Route
          path="/admin-dashboard"
          element={user ? <AdminDashboard /> : <Login setUser={setUser} />}
        />

        <Route
          path="/admin-applications"
          element={user ? <AdminApplications /> : <Login setUser={setUser} />}
        />

        <Route
          path="/add-job"
          element={user ? <AddJob /> : <Login setUser={setUser} />}
        />

        {/* COMMON */}
        <Route path="/opportunities" element={<Opportunities />} />

        <Route path="/profile" element={<Profile />} />
        
        <Route path="/register" element={<Register />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;