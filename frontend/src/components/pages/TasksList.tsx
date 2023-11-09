import DateTimeText from '../features/Text/DateTimeText';
import Text from '../features/Text/Text';
import styled from "@emotion/styled";
import Button from '../features/Buttons/Button';
import tomato from '../../images/tomato-icon.png';
import { NavLink } from 'react-router-dom';
import { useContext } from 'react';

import { TasksContext } from '../../hooks/useContext/taskContext';

const TaskList: React.FC = () => {

  const {tasks, setFilter} = useContext(TasksContext);

  const TaskContainer = styled.div`
    border: 1px solid red;
    border-radius: 4px;
    padding: 15px;
    margin: 8px 0;
    display: flex;
    flex-direction: column;
`;

  const ButtonWrapper = styled.div`
    display: flex;
    margin: 5px;
  `;

  return <div>
  <ButtonWrapper>
    <Button icon={tomato} children="completed" handleOnClick={() => setFilter('completed')} />
    </ButtonWrapper>
    <ButtonWrapper>
    <Button icon={tomato} children="uncompleted" handleOnClick={() => setFilter('uncompleted')} />
    </ButtonWrapper>
    <ButtonWrapper>
    <Button icon={tomato} children="all" handleOnClick={() => setFilter('all')} />
  </ButtonWrapper>

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
