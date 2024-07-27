import { useState } from "react"
import { TextField } from "../../components/input/text-field"
import { InputDate } from "../../components/input/input-date"
import { InputNumber } from "../../components/input/input-number"
import { ZoomMeeting } from "../../interfaces/zoom-meeting-dto"
import axios from "axios"
import InformationModal from "../../components/modal/information-modal"
import { useNavigate } from "react-router-dom"

export function CreateMeeting() {
    const [topic, setTopic] = useState<string>("")
    const [agenda, setAgenda] = useState<string>("")
    const [start_time, setStartTime] = useState<Date>(new Date)
    const [duration, setDuration] = useState<number>(0)
    const [ConfirmationModal, setConfirmationModal] = useState<boolean>(false)

    const clientID = 'zt6lhdUVTteosZ9p7x_NA'
    const redirectUri = encodeURIComponent('http://localhost:5173/zoom')
    const zoomAuthUrl = `https://zoom.us/oauth/authorize?response_type=code&client_id=${clientID}&redirect_uri=${redirectUri}`;

    const nav = useNavigate();

    const autenticarUsuario = (event : React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        event.preventDefault();
        let authWindow = window.open(zoomAuthUrl, '_blank', 'width=500,height=600');

        const handleMessage = (event: MessageEvent) => {
            if (event.data[0] === 'authenticated') {
                console.log(event.data)
                localStorage.setItem("token", event.data[1].access_token)
                window.removeEventListener('message', handleMessage)
                authWindow?.close()
                AgendarReuniaoZoom()
            }
        }
        window?.addEventListener('message', handleMessage)
    }

    const AgendarReuniaoZoom = async () => {
        try {
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
            await axios.post("http://localhost:8080/meeting/schedule", meeting,
                {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    }
                }
            ).then(resp => {
                if (resp.status == 200) {
                    setConfirmationModal(true)
                }
            }).catch(error => {
                console.log(error)
            })
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <>
            <form className="inline-flex flex-col center space-y-4 ">
                <h1>Schedule Meeting</h1>
                <TextField name={"topic: "} value={topic} setvalue={setTopic} type="text" />
                <TextField name={"agenda: "} value={agenda} setvalue={setAgenda} type="text" />
                <InputDate name="Date" setValue={setStartTime} value={start_time} />
                <InputNumber name="Duration" setValue={setDuration} value={duration} />
                <button onClick={(e) => autenticarUsuario(e)}>Agendar</button>
            </form>

            {ConfirmationModal && (
                <InformationModal onConfirm={() => nav("/")} confirmText="Ok" message={"Meeting Created successfully"}/>
            )}
        </>
    )
}