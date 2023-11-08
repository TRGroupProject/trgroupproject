import DateTimeText from '../features/Text/DateTimeText';
import Text from '../features/Text/Text';
import styled from "@emotion/styled";
import Button from '../features/Buttons/Button';
import tomato from '../../images/tomato-icon.png';
import { NavLink } from 'react-router-dom';
import { useContext } from 'react';
import { TasksContext } from '../../hooks/useContext/taskcontext';

const TaskList: React.FC = () => {

  const tasks = useContext(TasksContext);

  const TaskContainer = styled.div`
    border: 1px solid red;
    border-radius: 4px;
    padding: 15px;
    margin: 8px 0;
    display: flex;
    flex-direction: column;
`;

console.log("tasks", tasks)

  return <div>
  {tasks?.map((task) => (
    <>
      <TaskContainer key={task.taskId}>
        <Text children={task.title} type="heading" />
        <Text children={task.description} type="body" />
        <DateTimeText dateTime={new Date(task.calendarStartDateTime)} format={'time'} />
        <DateTimeText dateTime={new Date(task.calendarStartDateTime)} format={'date'} />
        <div>
          <NavLink to={`/tasks/${task.taskId}`}>
            <Button icon={tomato} children="START TIMER" handleOnClick={() => {}} />
          </NavLink>
        </div>
      </TaskContainer>
    </>
  ))}
  </div>
};

export default TaskList;
