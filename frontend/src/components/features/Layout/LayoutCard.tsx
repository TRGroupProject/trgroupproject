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

interface LayoutCardProps {
  children: ReactElement[];
  position?: "top" | "center";
}

const LayoutCard: React.FC<LayoutCardProps> = ({ children, position }) => {
  return (
    <Card position={position}>{children}</Card>
  );
};

export default LayoutCard;
