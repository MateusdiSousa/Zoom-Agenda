import api from "../variables/api";

export const meetingService = {
    getMeeting: async function getAllMeeting() {
        return await api.get("meeting")
    },

    deleteMeeting: async function deleteMeeting(id: string) {
        await api.delete(`meeting/${id}`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        }).then((resp) => {
            console.log(resp)
        }).catch(error => {
            console.log(error)
        })
    }
}