import internal from "stream"

export interface meeting {
    id : string
    topic : string
    agenda : string
    start_time : Date
    duration_minutes : internal
    join_url : string
    requester : string
    participants : any
    meeting_id : number
}