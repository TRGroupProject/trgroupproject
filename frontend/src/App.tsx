import { BrowserRouter } from "react-router-dom";
import {Router} from "./components/router/Router";
import { GoogleOAuthProvider, } from '@react-oauth/google';
import { TasksContext } from "./hooks/useContext/taskContext";
import { useEffect, useState } from "react";
import useFetchTasks from "./hooks/useFetchTasks";

const client_id = import.meta.env.VITE_REACT_APP_CLIENT_ID;
const endpoint = import.meta.env.VITE_FETCH_API_ENDPOINT_DEV;

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

const App: React.FC = () => {

  useEffect(() => {
    document.title="Pomodoro App";
  });

  const [ userId, setUserId] = useState<string>('')


  const { data } = useFetchTasks(
    endpoint,
    userId, // user Id as authorization value
    userId  // user Id
  );

  useEffect(() => {
    setTasks(data);
    setUserId('123abc');
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