import './App.css'
import { BrowserRouter as Router } from 'react-router-dom'
import { PublicRoutes } from './routes/public_routes'
import { Navbar } from './components/nav-bar'

function App() {

  return (
    <Router>
      <Navbar/>
      <PublicRoutes/>
    </Router>
  )
}

export default App
