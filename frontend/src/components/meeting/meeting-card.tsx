import { useState } from "react";
import { meeting } from "../../interfaces/meeting";
import { meetingService } from "../../services/meetingServices";
import ConfirmationModal from "../modal/confirmation-modal";

export function MeetingCard(props : {meeting : meeting, deleteComponent : Function }) {
    const [modalDelete, setModalDelete] = useState<boolean>(false);

    const DeleteMeeting = () => {
        meetingService.deleteMeeting(props.meeting.meeting_id).then(() => {
            props.deleteComponent(props.meeting.id)
        })
    }

    let start_time = new Date(props.meeting.start_time);

    const convertDateToBrasil = (date: Date) => {
        const novaData = new Date(date.getTime() - 180 * 60 * 1000);
        return novaData;
    }

    start_time = convertDateToBrasil(start_time);
    return (
        <>
            <div className="bg-base-300 m-2 rounded-md flex items-center justify-between">
                <div className="">
                    <div className="font-bold flex p-2">{props.meeting.topic}</div>
                    <ul className="flex">
                        <li className="p-2 flex">Topic: {props.meeting.topic}</li>
                        <li className="p-2 flex">Agenda: {props.meeting.agenda}</li>
                        <li className="p-2 flex">Date: {props.meeting.start_time.substring(0, 10).replace("-", "/").replace("-", "/")}</li>
                        <li className="p-2 flex">Hour: {start_time.getHours()}:{start_time.getMinutes()}</li>
                        <li className="p-2 flex">Duration: {props.meeting.duration_minutes}</li>
                    </ul>
                </div>
                <div>
                    <a href={props.meeting.join_url} target="_blank" className="btn p-2 mr-5">Join</a>
                    <button onClick={() => setModalDelete(true)}>Delete</button>
                </div>
            </div>

            {modalDelete && (
                <ConfirmationModal 
                message={`Do you really desire to delete meeting "${props.meeting.topic}"?`}
                cancelText="No" confirmText="Yes" 
                onConfirm={() => DeleteMeeting()}
                onCancel={() => setModalDelete(false)} />
            )}
        </>
    )
}