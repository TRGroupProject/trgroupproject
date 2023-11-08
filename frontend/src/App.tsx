import { useEffect } from "react";
import { BrowserRouter } from "react-router-dom";
import {Router} from "./components/router/Router";
import { GoogleOAuthProvider } from '@react-oauth/google';
import { TasksContext } from "./hooks/useContext/taskcontext";
import { useEffect, useState } from "react";
import useFetchTasks from "./hooks/useFetchTasks";

const client_id = import.meta.env.VITE_REACT_APP_CLIENT_ID;

const App: React.FC = () => {
  useEffect(() => {
    document.title="Pomodoro App";
  });

  const endpoint = 'http://localhost:8080/api/v1/tests/';
  const { data } = useFetchTasks(endpoint);

  type UserPomodoroTask = {
    taskId: string;
    googleUserId: string;
    googleEventId: string;
    title: string;
    description: string;
    calendarStartDateTime: string;
    pomodoroStartDateTime: string | null;
    pomodoroEndDateTime: string | null;
  }

  useEffect(() => {
    setTasks(data);
    }, [data]);

  const [ tasks, setTasks] = useState<UserPomodoroTask[]>(data)

  return (
    <>
      <TasksContext.Provider value={tasks}>
        <GoogleOAuthProvider clientId={client_id}>
          <BrowserRouter>
            <Router />
          </BrowserRouter>
        </GoogleOAuthProvider>
      </TasksContext.Provider>
    </>
  )
}

export default App;