import { CardHome } from "../components/home/card-home";
import { FaCalendar, FaUser } from "react-icons/fa";

export function Home() {
    return (
        <>
            <div className="flex justify-center items-center h-screen">
                <div className="flex justify-around space-x-8 font-medium">
                <CardHome titleCard="Users" buttonText="List of users" icon={<FaUser/>} buttonLink="list-user"/>
                <CardHome titleCard="Meeting" buttonText="Schedule" icon={<FaCalendar/>} buttonLink="schedule"/>
                </div>
            </div>
        </>
    )
}