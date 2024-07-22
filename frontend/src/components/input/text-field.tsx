interface TextFieldProps {
    name: string
    value : string 
    setvalue : Function
    type : string | "text"
}

export function TextField(props : TextFieldProps){
    return(
    <div className="flex space-x-3">
        <label>{props.name}</label>
        <input className="bordaInput items-center w-72 h-auto p-1 rounded-md border-2"
            type={props.type} value={props.value} onChange={(e) => props.setvalue(e.target.value)} />
    </div>
    )
}