import { useState } from "react";
import { meeting } from "../../interfaces/meeting";
import { meetingService } from "../../services/meetingServices";
import ConfirmationModal from "../modal/confirmation-modal";
import { MeetingEditModal } from "./meeting-edit-modal";
import { convertDateToBrasil } from "../../services/convertDate";

export function MeetingCard(props : {meeting : meeting, deleteComponent : Function , getMeeting : Function}) {
    const [modalDelete, setModalDelete] = useState<boolean>(false);
    
    const DeleteMeeting = () => {
        meetingService.deleteMeeting(props.meeting.meeting_id).then(() => {
            props.deleteComponent(props.meeting.id)
            setModalDelete(false);
            props.getMeeting();
        })
    }

    let start_time = new Date(props.meeting.start_time);

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
                <div className="space-x-5 mr-4">
                    <a href={props.meeting.join_url} target="_blank" className="btn">Join</a>
                    <MeetingEditModal getMeeting={props.getMeeting} meeting={props.meeting}/>
                    <button className="btn bg-warning btn-warning" onClick={() => setModalDelete(true)}>Delete</button>
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