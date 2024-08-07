import { CgAttachment } from "react-icons/cg";
import { Attachment } from "../../interfaces/attachment";
import { MdDelete } from "react-icons/md";
import { useState } from "react";
import ConfirmationModal from "../modal/confirmation-modal";
import api from "../../variables/api";


export function AttachmentModal(props: { attachments: Attachment[], getMeeting : Function}) {
    const [deleteModal, setDeleteModal] = useState<boolean>(false);
    const [selectedFile, setSelectedFile ] = useState<Attachment>();

    const openDeleteModal = (attachment : Attachment) => {
        setSelectedFile(attachment)
        setDeleteModal(true)
    }

    const deleteAttachment = async (id : string) => {
        await api.delete(`attachment/${id}`).then( () => {
            setDeleteModal(false)
            props.getMeeting()
        })
    }

    return (
        <>
            <details className="dropdown">
                <summary className="btn m-1"><CgAttachment /></summary>
                <ul className="menu dropdown-content bg-base-100 rounded-box z-[1] w-60 p-2 shadow">
                    {props.attachments.map((attachment) => {
                        const filename = attachment.filename.split("-")[0]
                        return <>
                            <li className="flex flex-row mb-2 items-center justify-between">
                                <a target="_blank" className="w-40" href={attachment.url} download={attachment.filename}>{`${filename}.${attachment.filetype}`}</a>
                                <button className="btn" onClick={() => openDeleteModal(attachment)}><MdDelete /></button>
                            </li>
                        </>
                    })}
                </ul>
            </details>
                    {deleteModal && selectedFile && (
                        <ConfirmationModal onCancel={() => setDeleteModal(false)}
                            message={`Do you desire to delete ${selectedFile.filename.split("-")[0]+"."+selectedFile.filetype} attachment?`}
                            confirmText="Yes"
                            cancelText="No"
                            onConfirm={() => deleteAttachment(selectedFile.id) } />
                    )}
        </>
    )
}