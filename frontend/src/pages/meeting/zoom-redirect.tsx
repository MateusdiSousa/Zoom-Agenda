import { useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import api from '../../variables/api';


const ZoomRedirect = () => {
  const [searchParams] = useSearchParams()
  const code = searchParams.get('code')

  useEffect(() => {
    api.post(`meeting/token/${code}`).then(resp => {
      console.log(resp)
      window.opener.postMessage(['authenticated',resp.data],'http://localhost:5173')
    }).catch(error => {
      console.log(error)
    })
  },[])


  return <>Usuário Autenticado </> 
};

export default ZoomRedirect;