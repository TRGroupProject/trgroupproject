import { useGoogleLogin } from '@react-oauth/google';
import * as React from 'react';

interface IHomeProps {
}

const Home: React.FC<IHomeProps> = () => {

  const login = useGoogleLogin({
    onSuccess: codeResponse => console.log(codeResponse)
  });

  return <>
     <button onClick={() => login()}>
          Log In Using Google
        </button>
  </>;
};

export default Home;
