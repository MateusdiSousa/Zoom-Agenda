import './App.css'
import { BrowserRouter as Router } from 'react-router-dom'
import { PublicRoutes } from './routes/public_routes'

function App() {

  return (
    <Router>
      <PublicRoutes/>
    </Router>
  )
}

export default App
