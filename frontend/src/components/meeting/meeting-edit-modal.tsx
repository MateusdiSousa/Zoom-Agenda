import { useEffect, useState } from "react";
import { InputDate } from "../input/input-date";
import { InputNumber } from "../input/input-number";
import { TextField } from "../input/text-field";
import { meeting } from "../../interfaces/meeting";
import { convertDateToBrasil } from "../../services/convertDate";
import { ZoomMeeting } from "../../interfaces/zoom-meeting-dto";
import axios from "axios";
import { FaEdit } from "react-icons/fa";

export function MeetingEditModal(props: { meeting: meeting, getMeeting: Function }) {
    const [topic, setTopic] = useState<string>(props.meeting.topic)
    const [agenda, setAgenda] = useState<string>(props.meeting.agenda)
    const [start_time, setStartTime] = useState<Date>(convertDateToBrasil(new Date(props.meeting.start_time)))
    const [duration, setDuration] = useState<number>(props.meeting.duration_minutes)

    useEffect(() => {
        let date : Date = convertDateToBrasil(new Date(props.meeting.start_time))
        date.setHours(date.getHours() -3)
        setStartTime(date)
    },[])

    const UpdateMeeting = async () => {
        console.log(start_time.toISOString())
        const meeting: ZoomMeeting = {
            type : 2,
            agenda: agenda,
            duration: duration,
            start_time: start_time.toISOString(),
            topic: topic,
            timezone: "America/Sao Paulo",
            settings: {
                host_video: true,
                participant_video: true,
                join_before_host: true,
                mute_upon_entry: true,
                watermark: false,
                use_pmi: false,
                approval_type: 0,
                audio: 'both',
                auto_recording: 'none',
            }
        }

        try {
            await axios.put(`http://localhost:8080/meeting/${props.meeting.meeting_id}`, meeting, {
                headers : {
                    Authorization : localStorage.getItem("token")
                }
            }).then(() => {
                props.getMeeting();
            })
        } catch (error) {
            console.log(error)
        }
    }
    
    return (

        <>
            <button className="btn" onClick={() => {
                let modal = document.getElementById(`${props.meeting.id}`)
                if (modal !== null) {
                    modal.showModal()
                }
            }
            }><FaEdit/></button>
            <dialog id={`${props.meeting.id}`} className="modal">
                <div className="modal-box">
                    <h3 className="font-bold text-lg">Edit Meeting</h3>
                    <div className="modal-action">
                        <form className="flex-col center space-y-4" method="dialog">
                            <TextField name="topic: " value={topic} setvalue={setTopic} type="text" />
                            <TextField name="agenda: " value={agenda} setvalue={setAgenda} type="text" />
                            <InputDate name="Date" setValue={setStartTime} value={start_time} />
                            <InputNumber name="Duration" setValue={setDuration} value={duration} />
                            <button className="btn" onClick={() => UpdateMeeting()}>Update</button>
                            <button className="btn">Close</button>
                        </form>
                    </div>
                </div>
            </dialog>
        </>

    )
}