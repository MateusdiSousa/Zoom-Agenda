interface InputDateProps {
    name: string
    value: number
    setValue: Function
}

export function InputNumber(props: InputDateProps) {
    return (
        <div className="flex space-x-3 items-center">
            <label>{props.name}:</label>
            <input 
            className="bordaInput items-center w-72 h-auto p-1 rounded-md border-2" 
            value={props.value}
            type="number" onChange={(e) => props.setValue(e.target.value)} />
        </div>
    )
}