import * as React from 'react';
import { useParams } from 'react-router-dom';

interface ITaskProps {
}

const Task: React.FC<ITaskProps> = () => {

  const taskId = useParams();
  console.log("taskId", taskId)

  return <>Task</>;
};

export default Task;
