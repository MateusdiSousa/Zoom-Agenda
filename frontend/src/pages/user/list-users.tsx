import { useEffect, useState } from "react"
import { UserDto } from "../../interfaces/user-dto";
import { userServices } from "../../services/userServices";
import { useNavigate } from "react-router-dom";

export function ListUser() {
    const [users, setUser] = useState<UserDto[]>([]);
    const nav = useNavigate();

    useEffect(() => {
        userServices.getAllUser().then(resp => {
            console.log(resp.data)
            setUser(resp.data)
        }).catch(error => {
            console.log(error)
        })
    }, []);

    const EditUser = async (user: UserDto) => {
        nav(`/edit-user`, { state: { key: user } });
    }

    const DeleteUser = async (id: string) => {
        userServices.removeUser(id).then(() => {
            const usersFiltered = users.map((user) => {
                if (user.id == id) {
                    user.active = false
                    return user;
                }
                return user
            })
            setUser(usersFiltered)
        }).catch(error => {
            console.log(error)
        })
    }

    return (
        <div className="flex flex-col justify-center">
            <h1>Users</h1>
            <table className="table table-auto">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Active</th>
                        <th>Edit</th>
                        <th>Remove</th>
                    </tr>
                </thead>
                <tbody>
                    {users.map(user => {
                        return (
                            <tr>
                                <td>{user.name}</td>
                                <td>{user.email}</td>
                                <td>{user.active ? "Yes" : "No"}</td>
                                <td><button onClick={() => EditUser(user)} className="btn">Edit</button></td>
                                <td><button onClick={() => DeleteUser(user.id)} className="btn">Remove</button></td>
                            </tr>
                        )
                    })}
                </tbody>
            </table>
            <div>
                <button className="btn" onClick={() => nav("/register-user")}>+ New User</button>
            </div>
        </div>
    )
}