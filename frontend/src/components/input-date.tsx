interface InputDateProps {
    name: string
    value: Date
    setValue: Function
}

export function InputDate(props: InputDateProps) {

    const handleDate = (dateString : string) => {
        console.log(dateString)
        let horas : number = parseInt(dateString.substring(11,13))
        horas = horas - 3
        dateString = dateString.substring(0,11) + horas + dateString.substring(13,16)
        props.setValue(new Date(dateString))
    }

    return (
        <div className="flex space-x-3 items-center">
            <label>{props.name}:</label>
            <input 
            className="bordaInput items-center w-72 h-auto p-1 rounded-md border-2" 
            value={props.value.toISOString().substring(0, 16)}
            type="datetime-local" onChange={(e) => handleDate(e.target.value)} />
        </div>
    )
}