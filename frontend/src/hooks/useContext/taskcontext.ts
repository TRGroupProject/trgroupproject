import { createContext } from "react";

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

  type TasksContextType = {
    tasks: UserPomodoroTask[];
    setFilter: (type: string) => void;
  };

  export const TasksContext = createContext<TasksContextType>({
    tasks: [],
    setFilter: () => {},
  });