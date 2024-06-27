import { useState } from "react";
import { CourseResponse } from "../type";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogTitle from "@mui/material/DialogTitle";
import CourseDialogContent from "./CourseDialogContent";
import { updateCourse } from "../api/coursesapi";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import Snackbar from "@mui/material/Snackbar";

type EditCourseProps = {
  CourseData: CourseResponse;
};

function EditCourse({ CourseData }: EditCourseProps) {
  const [SnackBarOpen, setSnackBarOpen] = useState(false);
  const [snackBarMessage, setSnackBarMessage] = useState("");

  const [course, setCourse] = useState<CourseResponse>({
    id: 0,
    code: "",
    title: "",
    season: "",
    year: "",
    instructorName: "",
    instructorId: null,
    status: "",
  });

  const [open, setOpen] = useState(false);

  const handleClickOpen = () => {
    setCourse({
      id: CourseData.id,
      code: CourseData.code,
      title: CourseData.title,
      season: CourseData.season,
      year: CourseData.year,
      instructorName: CourseData.instructorName,
      instructorId: CourseData.instructorId, 
      status: CourseData.status,
    });
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const queryClient = useQueryClient();

  const { mutate } = useMutation(updateCourse, {
    onSuccess: () => {
      setSnackBarOpen(true);
      queryClient.invalidateQueries(["courses"]);
    },
    onError: (err) => {
      console.error(err);
    },
  });

  const handleSave = () => {
    setSnackBarMessage(`Course ${course.title} has been updated successfully`);
    mutate(course);
    setCourse({
      id: 0,
      code: "",
      title: "",
      year: "",
      status: "",
      instructorName: "",
      instructorId: null, 
      season: "",
    });
    handleClose();
  };

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCourse({ ...course, [event.target.name]: event.target.value });
  };

  return (
    <>
      <button onClick={handleClickOpen}>Edit</button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Edit Course</DialogTitle>
        <CourseDialogContent course={course} handleChange={handleChange} />

        <DialogActions>
          <button onClick={handleClose}>Cancel</button>
          <button onClick={handleSave}>Save</button>
        </DialogActions>
      </Dialog>
      <Snackbar
        open={SnackBarOpen}
        autoHideDuration={5000}
        onClose={() => setSnackBarOpen(false)}
        message={snackBarMessage}
      ></Snackbar>
    </>
  );
}

export default EditCourse;
