import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import { InstructorResponse } from "../type";

type  AssignInstructorContent = {
  selectedInstructor: number;
  setSelectedInstructor: (value: number) => void;
  data: InstructorResponse[];
};



function AssignInstructorContent( {selectedInstructor, setSelectedInstructor, data}: AssignInstructorContent) {
  return (
    <>
      <FormControl fullWidth>
        <br></br>
        <InputLabel id="instructor-select-label">Instructor</InputLabel>
        <Select
          labelId="instructor-select-label"
          value={selectedInstructor}
          onChange={(e) => setSelectedInstructor(e.target.value as number)}
        >
          {data.map((instructor: InstructorResponse) => (
            <MenuItem key={instructor.id} value={instructor.id}>
              {instructor.name}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </>
  );
}

export default AssignInstructorContent;
