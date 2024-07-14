export interface ZoomMeeting {
    topic: string,
    agenda: string,
    start_time: Date,
    duration: number,
    timezone: string,
    settings: ZoomMeetingSettings
    type : number
}

export interface ZoomMeetingSettings {
    host_video: boolean,
    participant_video: boolean,
    join_before_host: boolean,
    mute_upon_entry: boolean,
    watermark: boolean,
    use_pmi: boolean,
    approval_type: number,
    audio: string,
    auto_recording: string
}