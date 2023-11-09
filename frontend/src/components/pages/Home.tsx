import { useEffect, useState } from "react";
import styled from "@emotion/styled";
import { useGoogleLogin } from "@react-oauth/google";
import Button from "../features/Buttons/Button";
import googleIcon from "../../assets/google-icon.png";
import LayoutCard from "../features/Layout/LayoutCard";
import { useNavigate } from "react-router-dom";

const Title = styled.h2`
  font-size: 24px;
  color: #333;
`;

const Home: React.FC = () => {
  const navigate = useNavigate()
  const [credentials, setCredentials] = useState('');

  const login = useGoogleLogin({
    onSuccess: (codeResponse) => {
      console.log(codeResponse);
      setCredentials(codeResponse.access_token);
      // navigate("/tasks");
    },
    scope: 'https://www.googleapis.com/auth/calendar.readonly',
    onError: (error) => console.error('Login Authentication Failed', error),
  });

  useEffect(() => {

    console.log("here")

    const fetchData = async () => {
      const url = 'http://localhost:8080/api/v1/events/users';
      const headers = new Headers();
      headers.append('Authorization', credentials);
      headers.append('Connection', 'keep-alive');
      headers.append('Access-Control-Allow-Origin', 'true');

      try {
        const response = await fetch(url, {
          method: 'POST',
          headers: headers,
        });

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }

        const data = await response.json();
        console.log('events data', data);

        return data;
      } catch (error) {
        console.error(error);
      }
    };

    if (credentials) {
      fetchData();
      navigate("/tasks");
    }
  }, [credentials, navigate]);

  return (
    <LayoutCard>
      <Title>Login with Google</Title>
      <Button icon={googleIcon} handleOnClick={() => login()}>
        Authenticate
      </Button>
    </LayoutCard>
  );
};

export default Home;