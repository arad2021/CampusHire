                document.getElementById('studentBtn').addEventListener('click', function() {
            document.getElementById('mainOptions').classList.add('hidden');
            document.getElementById('studentOptions').classList.remove('hidden');
        });

        document.getElementById('recruiterBtn').addEventListener('click', function() {
            document.getElementById('mainOptions').classList.add('hidden');
            document.getElementById('recruiterOptions').classList.remove('hidden');
        });

        function resetOptions() {
            document.getElementById('mainOptions').classList.remove('hidden');
            document.getElementById('studentOptions').classList.add('hidden');
            document.getElementById('recruiterOptions').classList.add('hidden');
        }