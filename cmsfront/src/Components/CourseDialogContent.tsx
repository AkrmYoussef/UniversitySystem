import { CourseResponse } from "../type";
import TextField from "@mui/material/TextField";
import Stack from "@mui/material/Stack";

type DialogFormProps = {
  course: CourseResponse;
  handleChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
};
function CourseDialogContent({ course, handleChange }: DialogFormProps) {
  return (
    <>
      <Stack spacing={2} mt={1}>
        <TextField
          label="Code"
          name="code"
          value={course.code}
          onChange={handleChange}
        />
        <TextField
          label="Title"
          name="title"
          value={course.title}
          onChange={handleChange}
        />
        <TextField
          label="status"
          name="status"
          value={course.status}
          onChange={handleChange}
        />
        <TextField
          label="Year"
          name="year"
          value={course.year}
          onChange={handleChange}
        />
        <TextField
          label="season"
          name="season"
          value={course.season}
          onChange={handleChange}
        />
        <br />
      </Stack>
    </>
  );
}
export default CourseDialogContent;
