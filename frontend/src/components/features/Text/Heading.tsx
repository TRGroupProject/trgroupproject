import styled from "@emotion/styled";

const StyledHeading = styled.h1<TextProps>`
  font-size: ${(props) => (props.size ? props.size + "px" : "24px")};
  font-weight: bold;
  color: #191919;
  margin: 8px 0;
`;

interface TextProps {
  children?: string;
  size?: number;
}

const Heading: React.FC<TextProps> = ({ children }) => {
  return <StyledHeading>{children}</StyledHeading>;
};

export default Heading;
