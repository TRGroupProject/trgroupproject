import * as React from 'react';
import NavHeader from '../header/NavHeader';
import { Outlet } from 'react-router-dom';


const MainLayout: React.FC = () => {
  return (
    <div>
      <NavHeader />
      <Outlet />
    </div>
  );
};

export default MainLayout;
