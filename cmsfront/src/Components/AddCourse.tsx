import { CourseResponse } from "../type";
import { useState } from "react";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { addCourse } from "../api/coursesapi";
import Snackbar from "@mui/material/Snackbar";
import CourseDialogContent from "./CourseDialogContent";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogTitle from "@mui/material/DialogTitle";

function AddCourse() {
  const [open, setOpen] = useState(false);
  const [SnackBarOpen, setSnackBarOpen] = useState(false);
  const [snackBarMessage, setSnackBarMessage] = useState("");
  const [course, setCourse] = useState<CourseResponse>({
    id:0, 
    code: "",
    title: "",
    season: "",
    year: "",
    instructorName: "",
    status: "",
  });

  // open the modal form
  const handleClickOpen = () => {
    setOpen(true);
  };

  // Close the modal form
  const handleClose = () => {
    setOpen(false);
  };

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCourse({ ...course, [event.target.name]: event.target.value });
  };

  const queryClient = useQueryClient();
  // Add inside the AddCourse component function
  const { mutate } = useMutation(addCourse, {
    onSuccess: () => {
      setSnackBarOpen(true);
      queryClient.invalidateQueries(["courses"]);
    },
    onError: (err) => {
      console.error(err);
    },
  });

  const handleSave = () => {
    setSnackBarMessage(`Course ${course.title} has been added successfully`);
    mutate(course);
    setCourse({
      id:0,
      code: "",
      title: "",
      year: "",
      status: "",
      instructorName: "",
      season: "",
    });
    handleClose();
  };

  return (
    <>
      <button onClick={handleClickOpen}>Add a new Course</button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>New Course</DialogTitle>
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
export default AddCourse;
