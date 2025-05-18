import React, { useState, useEffect } from "react";
import "./Home.css"; // Import the CSS file
import About from "./About"; // Import the About component
import UserManagement from "./UserManagement"; // Import the UserManagement component
import S1000D2Html from "./S1000D2Html"; // Import the UserManagement component

const Home = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false); // Default to false
  const [username, setUsername] = useState(""); // Store the username
  const [activePage, setActivePage] = useState("home"); // Track the active page

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  const handleLogout = () => {
    localStorage.removeItem("token"); // Clear token or session
    window.location.href = "/"; // Redirect to login page
  };

  // Fetch user info from the backend
  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/user-info", {
          method: "GET",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`, // Include the token in the request
          },
        });

        if (response.ok) {
          const data = await response.json();
          setUsername(data.username);
          setIsAdmin(data.role === "ROLE_ADMIN"); // Check if the role is admin
        } else {
          console.error("Failed to fetch user info");
        }
      } catch (error) {
        console.error("Error fetching user info:", error);
      }
    };

    fetchUserInfo();
  }, []); // Run only once when the component mounts

  const renderContent = () => {
    if (activePage === "about") {
      return <About />;
    } else if (activePage === "user-management") {
      return <UserManagement />; // Render the UserManagement component
    } else if (activePage === "s1000d2html") {
      return <S1000D2Html />; // Render the UserManagement component
    }

    // Default to home page content
    return (
      <div className="content">
        <h2>Home Page</h2>
        <p>This is the home page content.</p>
      </div>
    );
  };

  return (
    <div>
      <div className="header">
        <button onClick={toggleMenu} className="burger-button">
          ☰
        </button>
        <h1>Welcome, {username}!</h1>
      </div>
      {isMenuOpen && (
        <div className="menu">
          <ul className="menu-list">
            <li>
              <a
                href="#"
                onClick={() => {
                  setActivePage("home");
                  setIsMenuOpen(false); // Close the menu
                }}
              >
                Home
              </a>
            </li>
            <li>
              <a
                href="#"
                onClick={() => {
                  setActivePage("s1000d2html");
                  setIsMenuOpen(false); // Close the menu
                }}
              >
                About
              </a>
            </li>
            {isAdmin && (
              <li>
                <a
                  href="#"
                  onClick={() => {
                    setActivePage("user-management");
                    setIsMenuOpen(false); // Close the menu
                  }}
                >
                  User&nbsp;management
                </a>
              </li>
            )}
            <li>
              <a
                href="#"
                onClick={() => {
                  setActivePage("about");
                  setIsMenuOpen(false); // Close the menu
                }}
              >
                About
              </a>
            </li>
            <li>
              <button
                onClick={() => {
                  handleLogout();
                  setIsMenuOpen(false); // Close the menu
                }}
                className="logoutButton"
              >
                Logout
              </button>
            </li>
          </ul>
        </div>
      )}
      {renderContent()}
    </div>
  );
};

export default Home;