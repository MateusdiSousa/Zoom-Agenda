import { Route, Routes } from "react-router-dom";
import { RegisterUser } from "../pages/user/register-user";
import { ListUser } from "../pages/user/list-users";
import { EditUser } from "../pages/user/edit-user";
import { Home } from "../pages/home";
import { CreateMeeting } from "../pages/meeting/create-meeting";
import ZoomRedirect from "../pages/meeting/zoom-redirect";
import { MeetingList } from "../pages/meeting/meeting-list";

export function PublicRoutes(){
    return (
        <Routes>
            <Route path="/register-user" element={<RegisterUser/>}></Route>
            <Route path="/list-user" element={<ListUser/>}></Route>
            <Route path="/edit-user" element={<EditUser/>}></Route>
            <Route path="/" element={<Home/>}></Route>
            <Route path="/create-meeting" element={<CreateMeeting/>}></Route>
            <Route path="/zoom" element={<ZoomRedirect/>}></Route>
            <Route path="/schedule" element={<MeetingList/>}></Route>
        </Routes>
    )
}