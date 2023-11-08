import styled from "@emotion/styled";
import Heading from "../Text/Heading";
import Text from "../Text/Text";
import { NavLink } from "react-router-dom";
import { NavRoute } from "./NavRouteType";

const NavContainer = styled.nav`
  display: flex;
  padding: 0 20px;
  align-items: center;
  justify-content: space-between;
`;

const HeadingWrapper = styled.div`
  display: flex;
  justify-content: left;
  text-align: left;
  align-items: left;
  }
`;

const NavLinkWrapper = styled.div`
  display: inline;
  margin-right: 1.5em;

  & > * {
    text-decoration: none;
  }
`;

interface NavBarProps {
  routes: NavRoute[];
  heading?: string;
}

const NavBar: React.FC<NavBarProps> = ({ routes, heading }) => {
  return (
    <NavContainer>
      <HeadingWrapper>{heading && <Heading>{heading}</Heading>}</HeadingWrapper>
      <NavLinkWrapper>
        {routes.map((route) => (
          <NavLinkWrapper key={route.id}>
            <NavLink to={route.path}>
              <Text>{route.name}</Text>
            </NavLink>
          </NavLinkWrapper>
        ))}
      </NavLinkWrapper>
    </NavContainer>
  );
};

export default NavBar;
