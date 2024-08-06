import { CgAttachment, CgRemove } from "react-icons/cg";
import { Attachment } from "../../interfaces/attachment";
import { FiDelete } from "react-icons/fi";


export function AttachmentModal(props: {attachments : Attachment[]}) {

 


    return (
        <>
            <details className="dropdown">
                <summary className="btn m-1"><CgAttachment /></summary>
                <ul className="menu dropdown-content bg-base-100 rounded-box z-[1] w-52 p-2 shadow">
                    {props.attachments.map( (attachment) => {
                        const filename = attachment.filename.split("-")[0]
                        return <>
                            <li className="flex flex-row mb-2 space-x-2">
                                    <a target="_blank" href={attachment.url} download={attachment.filename}>{filename.substring(0,15)}</a>
                                    <button><CgRemove/></button>                                
                            </li>
                        </>
                    })}
                </ul>
            </details>
        </>
    )
}