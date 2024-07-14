import { UserDto } from "../interfaces/user-dto"
import api from "../variables/api"


export const userServices = {
    createUser: async function CreateUser(user: UserDto) {
        return await api.post("/user", user);
    },

    editUser: async function EditUser(user: UserDto) {
        return await api.put("/user", user);
    },

    getAllUser: async function GetAllUser() {
        return await api.get("/user");
    },

    removeUser: async function RemoveUser(id: string) {
        return await api.delete(`/user/${id}`)
    }
}