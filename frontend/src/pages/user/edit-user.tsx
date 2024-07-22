import { useEffect, useState } from "react";
import { TextField } from "../../components/input/text-field";
import { Checkbox } from "../../components/input/checkbox";
import { UserDto } from "../../interfaces/user-dto";
import { userServices } from "../../services/userServices";
import { useLocation, useNavigate } from "react-router-dom";
import InformationModal from "../../components/modal/information-modal";

export function EditUser() {
    const [name, setName] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [admin, setAdmin] = useState<boolean>(false);
    const [active, setActive] = useState<boolean>(false);
    const [id, setId] = useState<string>("")
    const location = useLocation();
    const nav = useNavigate();
    const [modal, setModal] = useState<boolean>(false);


    const editUser= (e : React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        e.preventDefault();
        const user : UserDto = {
            id : id ,
            active : active,
            admin : admin,
            email : email,
            name : name,
            password : ""
        }

        userServices.editUser(user).then(() => {
            setModal(true)            
        }).catch(error => {
            console.log(error)
        });
    }

    useEffect(() => {
        if (location.state && location.state.key) {
            const user : UserDto = location.state.key;
            setId(user.id)
            setName(user.name);
            setEmail(user.email);
            setAdmin(user.admin);
            setActive(user.active);
        }
    },[])

    return (
        <>
            <form className="inline-flex flex-col center space-y-4 ">
                <h1>Update User</h1>
                <TextField name={"Name: "} value={name} setvalue={setName} type="text" />
                <TextField name={"Email: "} value={email} setvalue={setEmail} type="email" />
                <Checkbox name={"Admin: "} value={admin} setvalue={setAdmin} />
                <Checkbox name={"Active: "} value={active} setvalue={setActive} />
                <button className="btn bg-pink-600 text-white" onClick={(e) => editUser(e)}>Edit </button> 
            </form>
            {modal && (
                <InformationModal confirmText="Ok" onConfirm={() => nav("/list-user")} message={"User already updated!"} />
            )}
        </>
    )
}