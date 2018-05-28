import * as React from "react";
import * as Action from './action';

export class MyUploader extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            selectedFile: null
        };

    }

    fileUploadHanlder = () => {
        Action.uploadNewFile(this.state.selectedFile);
    }

    fileSelectedHandler = event => {
        this.setState({
            selectedFile: event.target.files[0]
        });
    }

    render() {
        return ( 
            <div className = "container">
                <input type = "file" onChange = {this.fileSelectedHandler}/>
                <button onClick = {this.fileUploadHanlder}>Upload</button>
            </div>
        );
    }
}