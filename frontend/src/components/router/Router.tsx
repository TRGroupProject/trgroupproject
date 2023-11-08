import * as React from 'react';
import { Route, Routes } from 'react-router-dom';
import Home from '../pages/Home';
import TasksList from '../pages/TasksList';
import Report from '../pages/Reports/Report';
import Task from '../pages/Task';
import NotFound from '../notFound/NotFound';
import MainLayout from '../MainLayout/MainLayout';
import LoggedInOnly from './AuthenticatedRoute';

export const Router: React.FC = () => (
   <Routes>
    <Route path="/" element={<MainLayout />}>
        <Route path="" element={<Home />} />
        <Route path="/Report" element={
            <LoggedInOnly>
                <Report />
            </LoggedInOnly>
        } />
        <Route path="/Tasks" element={
            <LoggedInOnly>
                <TasksList />
            </LoggedInOnly>
        } />
        <Route path="/Task/:taskId" element={
            <LoggedInOnly>
                <Task />
            </LoggedInOnly>
        } />
        <Route path="*" element={<NotFound />} />
    </Route>
  </Routes>
);

