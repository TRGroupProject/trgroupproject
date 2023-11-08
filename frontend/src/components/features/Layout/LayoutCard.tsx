import styled from '@emotion/styled';
import { ReactElement } from 'react';

const Card = styled.div<LayoutCardProps>`
  background-color: #f3f3f3;
  border: 1px solid #e4e4e4;
  border-radius: 8px;
  box-shadow: 0px 2px 4px #0000001a;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  max-width: 300px;
  margin: 0 auto;
  margin-top: ${(props) => (props.position === "top" ? "10px" : "50px")};
`;

const Title = styled.h2`
  font-size: 24px;
  color: #333;
`;

interface LayoutCardProps {
  children: ReactElement | ReactElement[];
  title: string;
  position?: "top" | "center";
}

const LayoutCard: React.FC<LayoutCardProps> = ({ children, title, position }) => {
  return (
    <Card position={position}>
      <Title>{title}</Title>
      {children}
      </Card>
  );
};

export default LayoutCard;
