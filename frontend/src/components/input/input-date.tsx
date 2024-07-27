interface InputDateProps {
    name: string
    value: Date
    setValue: Function
}

export function InputDate(props: InputDateProps) {

    const handleDate = async (dateString : string) => {
        let date : Date = new Date(dateString);
        date.setHours(date.getHours()-3);
        props.setValue(date)
    }

    return (
        <div className="flex space-x-3 items-center">
            <label>{props.name}:</label>
            <input 
            className="bordaInput items-center w-72 h-auto p-1 rounded-md border-2" 
            value={props.value.toISOString().substring(0,16)}
            type="datetime-local" onChange={(e) => handleDate(e.target.value)} />
        </div>
    )
}