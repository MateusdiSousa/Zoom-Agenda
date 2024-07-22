import { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import { meeting } from "../../interfaces/meeting"
import { MeetingCard } from "../../components/meeting/meeting-card";
import { meetingService } from "../../services/meetingServices";

export function MeetingList() {
    const [meetings, setMeetings] = useState<meeting[]>([]);

    useEffect(() => {
        meetingService.getMeeting().then(resp => {
            if (resp.status === 200) {
                console.log(resp.data)
                setMeetings(resp.data)
            }
        })
    }, [])

    const removeMeeting = (id: string) => {
        setMeetings(
            meetings.filter(meeting => {
                return meeting.id != id;
            })
        )
    }

    return (
        <>
            <>
                {meetings.map(meeting => {
                    return (
                        <MeetingCard
                            meeting={meeting}
                            deleteComponent={removeMeeting}
                        />
                    )
                })}
            </>
            <div>
                <Link to={"/create-meeting"}><button>Create Meeting</button></Link>
            </div>
        </>
    )
}