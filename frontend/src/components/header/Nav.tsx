import * as React from 'react';
import { NavLink } from 'react-router-dom';


const Nav: React.FC = () => {
  return (
    <>
      <NavLink to="/Report">
        Report
      </NavLink>
      <NavLink to="/Tasks">
        Tasks
      </NavLink>
    </>
  );
};

export default Nav;
