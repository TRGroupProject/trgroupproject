import NavBar from '../features/NavBar/NavBar';
import { Outlet } from 'react-router-dom';
import { routes } from '../router/Routes';

const MainLayout: React.FC = () => {
  return (
    <div>
      <NavBar heading="Pomodoro App" routes={routes} />
      <Outlet />
    </div>
  );
};

export default MainLayout;
