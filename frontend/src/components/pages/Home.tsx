import { useGoogleLogin } from "@react-oauth/google";
import Button from "../features/Buttons/Button";

const Home: React.FC = () => {
  const login = useGoogleLogin({
    onSuccess: (codeResponse) => console.log(codeResponse),
    scope: "https://www.googleapis.com/auth/calendar.readonly",
    onError: (error) => console.error("Login Authentication Failed", error)
  });

  return (
    <>
      <Button 
        handleOnClick={() => login()}
      >
        Login in with Google
      </Button>
    </>
  );
};

export default Home;
