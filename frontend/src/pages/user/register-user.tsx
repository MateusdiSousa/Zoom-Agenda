import { useState } from "react";
import { TextField } from "../../components/text-field";
import { Checkbox } from "../../components/checkbox";
import { UserDto } from "../../interfaces/user-dto";
import { userServices } from "../../services/userServices";
import { useNavigate } from "react-router-dom";
import InformationModal from "../../components/information-modal";

export function RegisterUser() {
    const [name, setName] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("")
    const [admin, setAdmin] = useState<boolean>(false);
    const [modal, setModal] = useState<boolean>(false);
    const nav = useNavigate()

    const createUser= (e : React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        e.preventDefault();
        const user : UserDto = {
            id : "",
            active : true,
            admin : admin,
            email : email,
            name : name,
            password : password
        }

        userServices.createUser(user).then(() => {
            setModal(true);
        }).catch(error => {
            console.log(error)
        });

    }

    return (
        <>
            <form className="inline-flex flex-col center space-y-4 ">
                <h1>Regiter User</h1>
                <TextField name={"Name: "} value={name} setvalue={setName} type="text" />
                <TextField name={"Email: "} value={email} setvalue={setEmail} type="email" />
                <TextField name={"Password: "} value={password} setvalue={setPassword} type="password" />
                <Checkbox name={"Admin: "} value={admin} setvalue={setAdmin} />
                <button className="btn bg-pink-600 text-white" onClick={(e) => createUser(e)}>Register </button> 
            </form>
            {modal && (
                <InformationModal confirmText="Ok" onConfirm={() => nav("/list-user")} message={"User already registered!"} />
            )}
        </>
    )
}